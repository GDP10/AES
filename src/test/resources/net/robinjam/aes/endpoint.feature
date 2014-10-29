Feature: Customise consumer endpoint
         As a system administrator
         I want to be able to customise the endpoint that the consumer listens on
         So that I can run multiple consumers on the same system without conflicts

   Scenario: Bind to endpoint that's in use
              Given a consumer is running on "http://localhost:9000/TestConsumer"
              When I start a consumer on "http://localhost:9000/TestConsumer"
              Then the consumer crashes

   Scenario: Bind to alternative endpoint
              Given a consumer is running on "http://localhost:9000/TestConsumer"
              When I start a consumer on "http://localhost:9001/TestConsumer"
              Then the consumer does not crash