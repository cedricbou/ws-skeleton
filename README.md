ws-skeleton
===========

A Web service skeleton for Activity oriented services. The web service is task oriented.

Create simple *Command* to describe a task request, write a *CommandHandler* to implement the task. The skeleton wires everything else for you.

A REST web service (POST) is created for each command. Commands can be processed batched.

An Hessian web service is also automatically available for each command.

The project provides some tools for queries as well. Just define a view object, and annotate it with a JPA constructor class request.

The skeleton provides automatically a REST web service (GET) which execute the request and return a list of view object.

For testing easily, an UI is automatically available. The UI for now is just a prototype and still has many flaws. For example it has limited support for nested objects in commands, and no support for list or arrays in commands.

examples
========

Coming soon ! :)

