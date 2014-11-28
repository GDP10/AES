Feature: Filter NOTAM by time
         As a flight planner
         I want to receive NOTAMs within a given time period
         So that I can filter out events that are not relevant to my flight plan

   Scenario: Subscribe to a time period and recieve NOTAMS registered for that period
              Given a broker is running
              When I start a consumer with a temporal range of "2014-06-09T00:00:00.000Z, 2014-06-09T01:00:00.000Z"
              And I start a producer with xml "/delta.xml"
              Then the consumer receives events with location "ABCD"
              And the consumer does not receive an event with location "EFGH"
