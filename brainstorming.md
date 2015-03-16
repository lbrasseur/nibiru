# Introduction #

The objective of this page is discussing ideas for future framework improvements


# Ideas #

  * Replace Validation API with JSR303.
  * Build a workflow module.
  * Create an Eclipse RPC UI implementation, for creating desktop apps.
  * Make it simpler!!! OSGi Blueprint requires too much XML! What about Guice+Peaberry?
    * Create DSLs/builders for different modules.
    * Move to JSR330 dependency injection model.
  * Create Maven archetypes. Integrate with PAX-Construct.
  * Think about what should be taken into account in order to make it multitenant. This would allow serving multiple customers with Nibiru.
  * Business Intelligence support. Utilities for easy multidimensional cubes editing (CRUD-like dynamic tables). Mondrian integration. Reporting.
  * Distributed, fail safe session using Terracotta.
  * Market for custom modules.
  * Migrate Vaadin 7.
  * Build a new UI abstraction layer. Create a property binding model (Java Commons)



Any idea/constructive criticism is welcome! Post! Post! Post!

# Finished! #
  * Make a non-OSGi version (the only component rewritten was the extension point service implementation)
  * Build a reports module
  * Create a factory for CRUD managers.