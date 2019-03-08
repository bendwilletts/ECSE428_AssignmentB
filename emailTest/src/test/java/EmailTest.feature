Feature: Send Email With Attachement

Scenario Outline: Send an email with a local file attached

Given I am logged into a Gmail account

When I compose an email

And I specify "<recipient>" as the recipient

And I attach local file "<file>" to the email

And I send the email

Then the recipient should receive the email with the attached file

Examples: 
    | recipient | file |
    | uday147@hotmail.com | testfile1.gif |
    | uday147@hotmail.com | testfile2.jpg |
    | uday147@hotmail.com | testfile3.png |
    | udaysahni@outlook.com | testfile1.gif |
    | udaysahni@outlook.com | testfile2.jpg |
    
Scenario Outline: Send an email with a cloud file attached

Given I am logged into a Gmail account

When I compose an email

And I specify "<recipient>" as the recipient

And I attach the cloud file "<file>" to the email

And I send the email

Then the recipient should receive the email with the attached file

Examples: 
    | recipient | file |
    | uday147@hotmail.com | testfile1.gif |
    | uday147@hotmail.com | testfile2.jpg |
    | uday147@hotmail.com | testfile3.png |
    | udaysahni@outlook.com | testfile1.gif |
    | udaysahni@outlook.com | testfile2.jpg |