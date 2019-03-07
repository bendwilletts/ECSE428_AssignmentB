Feature: Send Email With Attachement

Scenario: Send an email with a local file attached

Given I am logged into a Gmail account

When I compose an email

And I specify a recipient email 

And I attach a local file

Then the recipient should recieve the email with the attached file