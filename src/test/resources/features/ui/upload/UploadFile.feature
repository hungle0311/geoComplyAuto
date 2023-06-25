@ui @upload
Feature: Full flow UI of upload file

  @uploadValidFile
  Scenario Outline: Verify that user can upload the valid files successfully
    Given I open the upload page
    When I upload the file "<File>"
    Then I expect the response message is displayed as "<Message>"

    Examples:
      | File                                                                         | Message                                 |
      | /Users/hgle/Documents/GFT/geo-demo/src/test/resources/data/files/receipt.png | 1 file\nhas been successfully uploaded. |

  @uploadNoTerm
  Scenario Outline: Verify that user cannot upload the file without accepting term of service
    Given I open the upload page
    When I choose file "<File>" to upload
    Then I click submit button
    Then I expect the response message is displayed as "<Message>"

    Examples:
      | File                                                                         | Message                           |
      | /Users/hgle/Documents/GFT/geo-demo/src/test/resources/data/files/receipt.png | Please accept the Term of Service |

  @uploadaNoFile
  Scenario Outline: Verify that error message is displayed when clicking Submit File with no file selected
    Given I open the upload page
    Then I click submit button
    Then I expect the response message is displayed as "<Message>"

    Examples:
      | Message                    |
      | No file selected to upload |


  @uploadInvalidFile
  Scenario Outline: Verify that user cannot upload a file that is larger than 196.45 MB
    Given I open the upload page
    When I upload the file "<File>"
    Then I expect the response message is displayed as "<Message>"

    Examples:
      | File                                                                         | Message                                     | Note                                                           |
      | /Users/hgle/Documents/GFT/geo-demo/src/test/resources/data/files/receipt.png | This maximum file to be upload is 196.45 MB | This file is more than 196.46 MB that is larger than 196.45 MB |
