# Team3 MarkdownDoc

[![pipeline status](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc/badges/master/pipeline.svg)](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc/-/commits/master)
[![coverage report](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc/badges/master/coverage.svg)](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc/-/commits/master)

This document is currently intended for development use only.

## 1. Requirements

Supported Language: Java 11

## 2. Development Guidelines

Please adhere to the following guidelines:

1. Create a new branch, `develop/<keyword>`, before working on a new user story. Merge to master after testing completes.
1. Coding conventions
   1. Use `camelCase` for variables and methods.
   1. Use `PascalCase` for classes.
   1. A variable name should sufficiently explain the value it holds.
   1. Method names should start with a verb.
   1. Use **4 spaces** for indents.
   1. Comment your code. Also, add javadoc comments to public methods.
   1. Explain public interfaces at _wiki/API_.
1. Git conventions
   1. Your git commit message **must not be empty**. Commit messages should have the following format:
      ```
      Write title
   
      Programmer programming programs.
	  Coder coding codes.
      ```
	  Title should start with a present-tense verb and end without a period.
	  
	  Description is optional. There should be an empty line between a title and a description.
   1. You **must not** rebase or force-push _any_ remote branch.
      > It alteres the Git history.
   1. Merge requests need at least 1 appoval and test coverage over 85% to be merged.
      Approving your own MR does not count.
	  > As squash merge alteres the Git history, you should not squash-merge.

## 3. Notes

- CI runners are slow. The average time required for a pipeline to be completed is 6 to 15 minutes.
- Not all commits of master branch are deployable.
- The project uses 3rd party libraries.