Feature: Customise broker address
         As a system administrator
         I want to be able to customise the address of the broker than the AES clients connect to
         So that I can host the broker on a different server to the producers and consumers
         
   Scenario: Specify broker address to consumer
              Given a broker is running
              When I start a consumer with address "http://localhost:9001/TestConsumer"
              Then the consumer does not crash

   Scenario: Specify incorrect broker address to consumer
              Given a broker is running
              When I start a consumer with address "http://localhost:9001/TestConsumer"
              Then the consumer crashes
             
   Scenario: Specify broker address to producer
              Given a broker is running
              When I start a producer with address "http://localhost:9000/wsn/NotificationBroker"
              Then the producer does not crash

   Scenario: Specify incorrect broker address to producer
              Given a broker is running
              When I start a producer with address "http://localhost:9000/wsn/NotificationBroker"
              Then the producer crashes