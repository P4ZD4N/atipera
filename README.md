# GitHub Repository API (Atipera Recruitment Task)

Simple API, which allows you to retrieve information about non-fork GitHub repositories of entered user, including their names, owners, branches, and last commit SHAs.

## Table of Contents

- [Tech stack](#tech-stack)
- [Features](#features)
- [Requirements](#requirements)
- [Run](#run)
- [Usage](#usage)
- [Errors](#errors)
- [Running tests](#running-tests)

## Tech Stack

- Java 21
- Spring Boot 3
- Maven
- Lombok
- JUnit 5
- Mockito

## Features

- **Retrieving GitHub Repositories:** Provides a list of non-fork repositories for a given GitHub username and details for each, including repository name, owner login, branch names and last commit SHAs.
- **Handling Non-Existing GitHub User:** Returns a 404 error with a message if the specified GitHub user does not exist.
- **Handling User with No Repositories:** Returns a 404 error with a message if the specified GitHub user has no repositories.
- **Handling Unsupported Media Type:** Returns a 415 error with a message if the Accept header is set to a media type other than application/json.
- **Logging Different Cases:** Logs various scenarios with loggers.
- **Testing**: Uses JUnit, Mockito, and MockMvc for unit tests, mocking dependencies, and testing REST endpoints.

## Requirements

- Java 21 (or higher)
- GitHub personal access token (optional for higher rate limits)

## Run

Clone the project

```bash
git clone https://github.com/P4ZD4N/atipera.git
```

Navigate the project directory

```bash
cd /path/to/atipera
```

**Optional:** Configure GitHub Authentication Token. If you want to avoid usage limits of the GitHub REST API, you need to have a token. <a href="https://github.com/settings/tokens">Generate token here</a>. Then fill **github.auth.token** property with your token in **application.properties** file like below:

```bash
github.auth.token=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

Run the Application

```bash
./mvnw spring-boot:run
```

If Maven is installed globally:

```bash
mvn spring-boot:run
```

## Usage

#### Base URL

The base URL for the API is:

```bash
http://localhost:8080
```

#### Endpoint

List GitHub repositories for desired user:

```bash
GET /api/github/repositories/{username}
Accept: application/json
```

Where `{username}` is username of GitHub user, which repositories you want to retrieve. 

#### Example

```bash
GET /api/github/repositories/p4zd4n
Accept: application/json
```

```
[
    {
        "repo_name": "3-column-preview-card",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "e973ab1d4c0308c0483b121bb72ca3d075e5ec3d"
                }
            }
        ]
    },
    {
        "repo_name": "area-intruders",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "aa5da17cb9b857fb06c1d2f5690b004cdb3c5027"
                }
            }
        ]
    },
    {
        "repo_name": "atipera",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "663245ad93b15c6eba605c2fdfc5b467ea80a409"
                }
            }
        ]
    },
    {
        "repo_name": "bibliotheca-chudyana",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "dev",
                "last_commit": {
                    "sha": "a2d11429a2b6fdb02706f151de1760a8ba6904a6"
                }
            },
            {
                "name": "main",
                "last_commit": {
                    "sha": "a2d11429a2b6fdb02706f151de1760a8ba6904a6"
                }
            }
        ]
    },
    {
        "repo_name": "codewars",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "master",
                "last_commit": {
                    "sha": "6adc6dcd161fc308718703add0732a7bed516ea5"
                }
            }
        ]
    },
    {
        "repo_name": "four-card-feature-section",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "9e70d22895f7d23217dde78a6370f7f220d64f9f"
                }
            }
        ]
    },
    {
        "repo_name": "globogym",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "f5317ba1b42f23ec59fb59d0e29bf86350561e83"
                }
            }
        ]
    },
    {
        "repo_name": "huddle-landing-page-with-a-single-introductory-section",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "585eeda1d58b34fbc0b0c0ac7931c01a7e829772"
                }
            }
        ]
    },
    {
        "repo_name": "leetcode",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "445a91f179a857a9e20e6097321a3c17088fd6ab"
                }
            }
        ]
    },
    {
        "repo_name": "p4zd4n",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "19fc089da88cec5ccb0871df5d4a33a4a72c5ced"
                }
            }
        ]
    },
    {
        "repo_name": "PJATK",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "93df11e4c8cde3a1862619e7902c24f22ea64fa2"
                }
            }
        ]
    },
    {
        "repo_name": "product-preview-card-component",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "65b8adc9778c6fed57e90fba387e8f247841ff5c"
                }
            }
        ]
    },
    {
        "repo_name": "qr-code-component",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "main",
                "last_commit": {
                    "sha": "865ddb811d2a4a052cb891626a4e3cfe49aa1856"
                }
            },
            {
                "name": "master",
                "last_commit": {
                    "sha": "3b3825995c2c14a22da932ad43a001ce330aabf2"
                }
            }
        ]
    },
    {
        "repo_name": "stats-preview-card-component-main",
        "owner_login": "P4ZD4N",
        "branches": [
            {
                "name": "master",
                "last_commit": {
                    "sha": "da965d58e066e7d79f53f3ace67d3197dbb06b7e"
                }
            }
        ]
    }
]
```
## Errors

When an error occurs, the application will return a response with appropriate HTTP Status code and message with the following structure:

```
{
  "status": "${httpStatusCode}",
  "message": "${errorMessage}"
}
```

## Running Tests

Navigate the project directory

```bash
 cd /path/to/atipera
```

To run tests, run the following command

```bash
./mvnw test
```

If Maven is installed globally:

```
mvn test
```

