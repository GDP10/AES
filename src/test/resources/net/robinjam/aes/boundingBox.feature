Feature: Filter NOTAM by distance from a point
         As a flight planner
         I want to receive NOTAMs relating to a geographical location
         So that I can filter out events that are not relevant to my flight plan

   Scenario: Subscribe to a time period and recieve NOTAMS registered for that period
              Given a broker is running
              When I start a consumer with a bounding box with top left co-ordinates "0.0","0.0" and bottom right co-ordinates "1.0","1.0"
              And I start a producer with xml "/delta.xml"
              Then the consumer receives events with location "ABCD"
              And the consumer does not receive an event with location "EFGH"
