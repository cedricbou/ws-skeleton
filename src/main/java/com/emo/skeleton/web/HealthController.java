package com.emo.skeleton.web;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {

	@SuppressWarnings("unused")
	private static class Property {
		public final String key;
		public final String value;

		public Property(final String key, final String value) {
			this.key = key;
			this.value = value;
		}
	}

	private final static long MEGA = 1024 * 1024;

	private String mega(final long value) {
		DecimalFormat df = new DecimalFormat("#.00");
		df.format(0.912385);
		return df.format(value / (double) MEGA) + "Mb";
	}

	private static class Memory {
		public final String name;
		public final MemoryUsage usage;

		public Memory(final String name, final MemoryUsage usage) {
			this.name = name;
			this.usage = usage;
		}
	}

	@RequestMapping("/health")
	@ResponseBody
	public final List<Property> report(final HttpServletRequest req) {
		final List<Property> reports = new LinkedList<Property>();

		final List<Memory> mems = new LinkedList<Memory>();
		mems.add(new Memory("heap", ManagementFactory.getMemoryMXBean()
				.getHeapMemoryUsage()));
		mems.add(new Memory("nonheap", ManagementFactory.getMemoryMXBean()
				.getNonHeapMemoryUsage()));

		Iterator<MemoryPoolMXBean> iter = ManagementFactory
				.getMemoryPoolMXBeans().iterator();

		while (iter.hasNext()) {
			MemoryPoolMXBean item = iter.next();
			mems.add(new Memory(item.getName() + " Usage", item.getUsage()));
			if (null != item.getPeakUsage()) {
				mems.add(new Memory(item.getName() + " Peak Usage", item
						.getPeakUsage()));
			}
			if (null != item.getCollectionUsage()) {
				mems.add(new Memory(item.getName() + " Collection Usage", item
						.getCollectionUsage()));
			}

		}

		for (final Memory mem : mems) {
			reports.add(new Property(mem.name + ".init", mega(mem.usage
					.getInit())));
			reports.add(new Property(mem.name + ".used", mega(mem.usage
					.getUsed())));
			reports.add(new Property(mem.name + ".max",
					mega(mem.usage.getMax())));
			reports.add(new Property(mem.name + ".committed", mega(mem.usage
					.getCommitted())));
		}

		reports.add(new Property("req.content.type", req.getContentType()));
		reports.add(new Property("req.server.name", req.getServerName()));
		reports.add(new Property("req.server.port", Integer.toString(req.getServerPort())));
		reports.add(new Property("req.remote.user", req.getRemoteUser()));
		reports.add(new Property("req.remote.addr", req.getRemoteAddr()));
		reports.add(new Property("req.remote.host", req.getRemoteHost()));
		reports.add(new Property("req.auth.type", req.getAuthType()));
		
		return reports;
	}
}
