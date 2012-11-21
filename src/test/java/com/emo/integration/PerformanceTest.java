package com.emo.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.emo.sample.commands.NewClient;
import com.emo.skeleton.api.CommandApi;
import com.emo.utils.CommandUtils;
import com.emo.utils.EmbeddedServer;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.MetricName;

@Ignore("not relevant in mainstream tests")
public class PerformanceTest {

	private static final EmbeddedServer server = new EmbeddedServer();
	
	@BeforeClass
	public static void startServer() {
		server.startServer();
	}
	
	@AfterClass
	public static void stopServer() {
		server.stopServer();
	}
	
	@Test
	public void measurePerformanceForRest() throws IOException,
			InterruptedException, ExecutionException {

		// Warm-Up
		final Histogram warmUpRestHisto = Metrics.newHistogram(new MetricName(
				PerformanceTest.class, "h-rest-warmup")); 

		new RunRestCalls(warmUpRestHisto).run();
		printHistogram("WARM-UP REST", warmUpRestHisto);
		
		runRestPerf();
		runBatchRestPerf();
	}
	
	@Test
	public void measurePerformanceForHessian() throws IOException,
			InterruptedException, ExecutionException {

		final Histogram warmUpHessianHisto = Metrics.newHistogram(new MetricName(
				PerformanceTest.class, "h-hessian-warmup")); 
		
		new RunHessianCalls(warmUpHessianHisto).run();
		printHistogram("WARM-UP Hessian", warmUpHessianHisto);
		
		runHessianPerf();
	}
	
	private void runRestPerf() throws InterruptedException, ExecutionException {
		final Histogram h = Metrics.newHistogram(new MetricName(
				PerformanceTest.class, "h-rest-perf"));

		ExecutorService executor = Executors.newFixedThreadPool(20);

		final List<Future<?>> runs = new LinkedList<Future<?>>();

		for (int i = 0; i < 10; ++i) {
			runs.add(executor.submit(new RunRestCalls(h)));
		}

		for (final Future<?> run : runs) {
			run.get();
		}

		printHistogram("REST", h);
		
		executor.shutdown();		
	}

	private void runBatchRestPerf() throws InterruptedException, ExecutionException {
		final Histogram h = Metrics.newHistogram(new MetricName(
				PerformanceTest.class, "h-batch-rest-perf"));

		ExecutorService executor = Executors.newFixedThreadPool(20);

		final List<Future<?>> runs = new LinkedList<Future<?>>();

		for (int i = 0; i < 10; ++i) {
			runs.add(executor.submit(new RunBatchRestCalls(h)));
		}

		for (final Future<?> run : runs) {
			run.get();
		}

		printHistogram("BATCH REST", h);
		
		executor.shutdown();		
	}

	
	private void printHistogram(final String id, final Histogram h) {
		System.out.println(id + " results : ");
		System.out.println("min = " + nanoToMSec(h.min()) + "; max = " + nanoToMSec(h.max())
				+ "; mean = " + nanoToMSec(h.mean()) + "; std dev = " + h.stdDev() + "; count = " + h.count() + "; cumulated time = " + nanoToMSec(h.sum()));
	}
	
	private void runHessianPerf() throws InterruptedException, ExecutionException {
		final Histogram h = Metrics.newHistogram(new MetricName(
				PerformanceTest.class, "h-hessian-perf"));

		ExecutorService executor = Executors.newFixedThreadPool(20);

		final List<Future<?>> runs = new LinkedList<Future<?>>();

		for (int i = 0; i < 10; ++i) {
			runs.add(executor.submit(new RunHessianCalls(h)));
		}

		for (final Future<?> run : runs) {
			run.get();
		}

		printHistogram("Hessian", h);

		executor.shutdown();		
	}

	private static double nanoToMSec(final double nano) {
		return nano / 1000000.0;
	}

	
	private static class RunRestCalls implements Runnable {

		private final Histogram h;

		public RunRestCalls(final Histogram h) {
			this.h = h;
		}

		@Override
		public void run() {
			final JSONObject json = new JSONObject();
		
			final int max_iterations = 100;

			final long[] responses = new long[max_iterations];

			for (int i = 0; i < max_iterations; ++i) {
				final NewClient cmd = CommandUtils.randomNewClientCommand();
				json.put("clientCode", cmd.getClientCode());
				json.put("name", cmd.getName());
				json.put("street", cmd.getStreet());
				json.put("city", cmd.getCity());
				json.put("zip", cmd.getZip());
				json.put("countryCode", cmd.getCountryCode());

				final long triggered = System.nanoTime();

				given().content(json.toJSONString()).expect()
						.body(equalTo("ok")).when().post("/rest/commands/NewClient");

				responses[i] = System.nanoTime() - triggered;
			}

			for (int i = 0; i < max_iterations; ++i) {
				synchronized (h) {
					h.update(responses[i]);
				}
			}
		}
	}

	
	private static class RunBatchRestCalls implements Runnable {

		private final Histogram h;

		public RunBatchRestCalls(final Histogram h) {
			this.h = h;
		}

		@Override
		public void run() {
			final JSONObject[] jsons = new JSONObject[] {
				new JSONObject(),
				new JSONObject(),
				new JSONObject()
			};
			
			final int max_iterations = 100;

			final long[] responses = new long[max_iterations];

			for (int i = 0; i < max_iterations; ++i) {
				final JSONArray cmdHolders = new JSONArray();
				
				for(int j = 0; j < jsons.length; ++j) {
					final NewClient cmd = CommandUtils.randomNewClientCommand();
					jsons[j].put("clientCode", cmd.getClientCode());
					jsons[j].put("name", cmd.getName());
					jsons[j].put("street", cmd.getStreet());
					jsons[j].put("city", cmd.getCity());
					jsons[j].put("zip", cmd.getZip());
					jsons[j].put("countryCode", cmd.getCountryCode());
					
					final JSONObject holder = new JSONObject();
					holder.put("type", "NewClient");
					holder.put("command", jsons[j]);
					
					cmdHolders.add(holder);
				}

				final long triggered = System.nanoTime();

				given().content(cmdHolders.toJSONString()).expect()
						.body(equalTo("ok")).when().post("/rest/commands/batch");

				responses[i] = System.nanoTime() - triggered;
			}

			for (int i = 0; i < max_iterations; ++i) {
				synchronized (h) {
					h.update(responses[i]);
				}
			}
		}
	}

	
	private static class RunHessianCalls implements Runnable {

		private final Histogram h;

		public RunHessianCalls(final Histogram h) {
			this.h = h;
		}

		@Override
		public void run() {
			final String url = "http://localhost:8080/hessian/commands";

			final HessianProxyFactory factory = new HessianProxyFactory();
			CommandApi api = null;
			try {
				api = (CommandApi) factory.create(CommandApi.class, url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	
			final int max_iterations = 300;

			final long[] responses = new long[max_iterations];

			for (int i = 0; i < max_iterations; ++i) {
				final long triggered = System.nanoTime();

				api.processCommand(CommandUtils.randomNewClientCommand());
		
				responses[i] = System.nanoTime() - triggered;
			}

			for (int i = 0; i < max_iterations; ++i) {
				synchronized (h) {
					h.update(responses[i]);
				}
			}
		}
	}

}
