# Museum Management Software System

## Project Members
* Athmane Benarous
* Mohamed Elsamadouny
* Sasha Denouvilliez-Pech
* Saviru Perera
* Shidan Javaheri
* Shyam Desai

## Project Scope
This system is designed for a museum of a small town to support their community and provide better services. It allows visitors to login, browse artefacts, donate artefacts, request to loan artefacts, check loan status, book tickets, and book tours. It allows employees to create and move artefacts. It allows manager to manage employees and their shifts.

 - - -
 
# Deliverable 1

The domain model for the system and the key design decisions made for it are [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Domain-Model). The 15 most important requirements are listed [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Requirements). The use-case diagrams along with descriptions for the six most important requirements are listed [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Use-Case-Diagrams), and the use-case specifications for them are [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Use-Case-Specifications). The project report detailing meeting minutes and key decisions throughout the deliverable are [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Project-Report-Deliverable-1).

## Team Roles

The task of writing and analyzing elicited requirements was split as follows:
* **System:** Shyam Desai
* **Rooms + Artefacts:** Sasha Denouvilliez-Pech
* **Visitors:** Saviru Perera
* **Employees:** Athmane Benarous
* **Manager:** Mohamed Elsamadouny
* **Loaning + Donating:** Shidan Javaheri

The task of creating use case diagrams and their alternate scenarios was split as follows:
* **Login to System:** Saviru Perera
* **Create an Artefact:** Mohamed Elsamadouny
* **Book Tickets:** Sasha Denouvilliez-Pech
* **Book Tours:** Shyam Desai
* **Request to Donate an Artefact:** Athmane Benarous
* **Request to Loan an Artefact:** Shidan Javaheri

_Note: All major decisions were always discussed and made by the entire team during a meeting._

## Contributions
| Name             | Responsibilities                                                  | Hours (incl. meetings) |
|:----------------:|:----------------------------------------------------------------- |:----------------------:|
| Athmane Benarous | - Contributed to designing the domain model <br> - Contributed to keeping track of the meeting minutes <br> - Wrote the test cases for the artefact, communication, donation, employee, and loan classes <br> - Wrote testing documentation | 25 hours |
| Mohamed Elsamadouny |- Designed the domain model <br> - Wrote the test cases for Manager, Notification, OpenDay, Person and Room classes | 22 hours |
| Sasha Denouvilliez-Pech | - Designed the domain model <br> - Designed the database <br> - JPA Annotations <br> - Created issues <br> - Reviewed use-case diagrams and scenarios| 40 hours |
| Saviru Perera | - Designed the domain model <br> -Wrote Tests for Shift, Visitor, Ticket, Tour, and Schedule Classes  | 20 hours |
| Shidan Javaheri | - Reviewed and finalized requirements <br> - Designed the domain model <br> - Designed the database <br> - JPA Annotations <br> - Set up file structure and project connection to database <br> - Wrote Sample test file for ArtefactRepository <br> - Reviewed and edited all tests <br> - Edited Wiki page <br> - Asked questions on behalf of the group | 55 hours |
| Shyam Desai | - Designed the domain model <br> - Reviewed and finalized requirements <br> - Reviewed other teammates' use-case diagrams <br> - Populated and updated the GitHub Wiki page <br> - Wrote test case for the Visitor class | 25 hours |

- - -

# Deliverable 2

The API documentation for each controller method in the system is found [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/API-Documentation#api-documentation). The plan we followed while writing tests is detailed [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Test-Plan). Our backlog is seen [here](https://github.com/orgs/McGill-ECSE321-Fall2022/projects/21). The project report detailing meeting minutes is [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Project-Report-Deliverable-2#meeting-minutes), and the updates made to the class diagram are shown [here](https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Project-Report-Deliverable-2#update-to-uml-class-diagram).

## Team Roles

The task of writing classes and its tests were split as follows:
* **Artefact:** Sasha Denouvilliez-Pech
* **Communication:** Sasha Denouvilliez-Pech
* **Donation:** Mohamed Elsamadouny
* **Employee:** Saviru Perera
* **Loan:** Shidan Javaheri
* **Login:** Saviru Perera
* **Manager:** Shidan Javaheri
* **Notification:** Sasha Denouvilliez-Pech
* **OpenDay:** Mohamed Elsamadouny
* **Room:** Sasha Denouvilliez-Pech
* **Schedule:**  Athmane Benarous
* **Shift:**  Athmane Benarous
* **Ticket:** Shyam Desai
* **Tour:** Shyam Desai
* **Visitor:** Saviru Perera


## Contributions
| Name             | Responsibilities                                                  | Hours (incl. meetings) |
|:----------------:|:----------------------------------------------------------------- |:----------------------:|
| Athmane Benarous | - Shift and Schedule classes <br> - Handling final GitHub pull request issues <br> | 30 hours |
| Mohamed Elsamadouny | - OpenDay and Donation classes <br> - Wrote API documentation for Donation, OpenDay, Schedule, Shift, Employee, Visitor <br>  | 30 hours |
| Sasha Denouvilliez-Pech | - Setting the views in the Kanban board <br> - Writing initial issues and labels <br> - Artefact, Room, Communication, and Notification classes <br> - Review code and PRs <br> - Writing test plan | 45 hours |
| Saviru Perera | - Employee, Visitor, and Login classes <br> - Writing Service, Service Tests <br> - Writing Controllers and Controller Tests <br> | 40 hours |
| Shidan Javaheri | - Providing code examples and templates <br> - Static code reviews for all classes, and debugging assistance <br> - Loan and Manager classes <br> - Review code and PRs <br> - Writing API documentation <br> - Final wiki review <br> | 60 hours |
| Shyam Desai | - Writing the GitHub issues, service class, controller class, service tests, and integration tests for Tour and Ticket <br> - Keeping track of the meeting minutes <br> - Writing the API documentation in the wiki <br> | 32 hours |

- - -

# Deliverable 3

The architecture model can be found [here].(https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/API-Documentation#api-documentation). The implementation of the Web Frontend can be found [here].(https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Test-Plan). The Build System and CI specifications can be found [here]. The key design decisions, additional features, and changes to the backend can be found [here].(https://github.com/orgs/McGill-ECSE321-Fall2022/projects/21). The project report detailing meeting minutes is [here].(https://github.com/McGill-ECSE321-Fall2022/project-group-09/wiki/Project-Report-Deliverable-2#meeting-minutes)

## Team Roles

The task of writing web pages were split as follows:
* **Artefact:** Sasha Denouvilliez-Pech
* **Room:** Sasha Denouvilliez-Pech
* **Ticket:** Shyam Desai
* **Tour:** Shyam Desai
* **OpenDay:** Mohamed Elsamadouny
* **Donation:** Mohamed Elsamadouny
* **Employee:** Saviru Perera
* **Visitor:** Saviru Perera
* **Loan:** Shidan Javaheri
* **Login:** Shidan Javaheri
* **Manager:** Shidan Javaheri
* **Notification:** Athmane
* **Schedule:**  Athmane Benarous
* **Shift:**  Athmane Benarous
* **Communication:** Athmane
* **Main navigation bar:** Athmane



## Contributions
| Name             | Responsibilities                                                  | Hours (incl. meetings) |
|:----------------:|:----------------------------------------------------------------- |:----------------------:|
| Athmane Benarous | -  <br> -  <br> | XX hours |
| Mohamed Elsamadouny | -  <br> -  <br>  | XX hours |
| Sasha Denouvilliez-Pech | - View, create, update, and move artefacts <br> - Room tables (Dashboard) <br> - Wiki <br> - Review code and PRs <br> | 35 hours |
| Saviru Perera | -  <br> -  <br>  | XX hours |
| Shidan Javaheri | -  <br> -  <br>  | XX hours |
| Shyam Desai | -  <br> -  <br>  | XX hours |

