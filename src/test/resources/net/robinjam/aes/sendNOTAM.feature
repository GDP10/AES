Feature: Send NOTAM data to consumer
         As a flight planner
         I want to receive NOTAMs based on subscriptions to relevant topics
         So that I can filter out events that are not relevant to my flight plan

   Scenario: Subscribe to topics, receive only relevant events
              Given a broker is running
              When I start a consumer with topics "ABCD, EFGH"
              And I start a producer with xml "/delta.xml"
              Then the consumer receives events with locations "ABCD, EFGH"
              And the consumer does not receive an event with location "IJKL"