Feature: Send Email With Attachment  
    As an email account owner
    I can send an email with a local image attachment
    So recipient(s) can receive my email with an image attached  

    Scenario Outline: Send an email to a recipient "<recipient>" with a local file "<file>" attached
        Given I am logged into a Gmail account
        When I compose an email
        And I specify "<recipient>" as the recipient
        And I attach a local file "<file>" to the email
        And I send the email
        Then the recipient should receive the email with the attached file

    Examples: 
        | recipient | file |
        | uday147@hotmail.com | testfile1.gif |
        | uday147@hotmail.com | testfile2.jpg |
        | uday147@hotmail.com | testfile3.png |
        | udaysahni@outlook.com | testfile1.gif |
        | udaysahni@outlook.com | testfile2.jpg |
    
    Scenario Outline: Send an email to a recipient "<recipient>" and a CC'd email address "<ccrecipient>" with a local file "<file>" attached
        Given I am logged into a Gmail account
        When I compose an email
        And I CC "<ccrecipient>" on the email
        And I specify "<recipient>" as the recipient
        And I attach a local file "<file>" to the email
        And I send the email
        Then the recipient should receive the email with the attached file

    Examples: 
        | recipient | ccrecipient | file |
        | uday147@hotmail.com | udaysahni@outlook.com | testfile1.gif |
        | uday147@hotmail.com | udaysahni@outlook.com | testfile2.jpg |
        | uday147@hotmail.com | uday147@hotmail.com | testfile3.png |
        | udaysahni@outlook.com | udaysahni@outlook.com | testfile1.gif |
        | udaysahni@outlook.com | uday147@outlook.com | testfile2.jpg |
    
    Scenario Outline: Send an email with an invalid recipient email "<recipient>" with a local file "<file>" attached
        Given I am logged into a Gmail account
        When I compose an email
        And I specify an invalid recipient "<recipient>"
        And I attach a local file "<file>" to the email
        And I send the email
        Then I should receive an error message notifying me that the email was not recognised

    Examples: 
        | recipient | file |
        | uday147 | testfile1.gif |
        | uday14 | testfile2.jpg |
        | uday147@hotmail | testfile3.png |
        | udaysahnioutlook.com | testfile1.gif |
        | @outlook.com | testfile2.jpg |