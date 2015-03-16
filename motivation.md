# Introduction #

This section explains why we use Nibiru at Oxen. Is a way to clarify which were our ideas when building it and avoiding confusion about where it can be helpful.

Weren't saying "use this framework for everything". Wer'e just saying "It helped us in this way". If you have a similar business scenario, go ahead! The framework is open. If you have a different scenario but think that Nibiru can be helpful, go ahead too! And post some comment about it :)

The framework is still unfinished. The motivation for releasing as open source before finishing it was receiving feedback from the community.

# Motivation #

At Oxen (among other things), we develop custom solutions for customers. We previously worked in many software factories where code reuse was inexistent. Now we face a similar business scenario. How to avoid it?

Obviously there is many problems with writing all the applications from scratch. Just to name a few:

  * You require a considerable initial effort defining the application architecture.
  * Reusing modules from different projects is really hard. Because of this, software factories seldom create products or modules.

With Nibiru, we identified a set of services which are common to most custom commercial applications. We specified them as Java interfaces and packaged them as OSGi. We started with some implementations (Vaadin, Hibernate, etc.).

This way, we face the first problem, the application skeleton definition, by having a pre-defined skeleton. And some common patterns implemented. For example, all the components are injected using IoC, so we could change even the container. For UI, Nibiru implements the MVP pattern. The user session is provided as a service. And so on. This way, we reduce architecture elaboration effort.

Let's talk about the second problem, code reutilization. Imagine the following scenario. You have two customers which requires you a payroll module (or whatever funcionality). But client A wants to run a desktop app using Eclipse RCP and client B a Web app using Vaadin.

If you would have a prebuilt payroll module, you could sell it to both customers. But it would require supporting both architectures. Here is where Nibiru comes in play.

Nibiru provides abstraction layers for these aspects. Following this example, the HTTP session is hidden behind a "Session" service (exposed as OSGi service). The basic UI elements are hidden behind interfaces and factories too.

However, this could restrict you, for example, to basic UI interface functionality. But if your customer wants pay more for a custom, very nice and cool UI, Nibiru doesn't limits you. Just use the Event Bus and custom MVP views. But now you have the option: build a standard (cheap or licensed) module or a custom one.

By the way, OSGi motivation was about adding dynamically modules to a running application. Your customer could have a unique Nibiru app and add services as modules as needed. But thanks to IoC, if your customer doesn't want OSGi, Nibiru can work on non-OSGi environments too.