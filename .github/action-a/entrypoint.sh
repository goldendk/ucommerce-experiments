#!/bin/sh -l
# this is a n example of the hello world github actions tutorial.
sh -c "echo Hello world my name is $INPUT_MY_NAME"

#
#main.yml file - should be placed in /github/workflows/main.yml
#
#name: A workflow for my Hello World file
#on: push
#jobs:
#  build:
#    name: Hello world action
#    runs-on: ubuntu-latest
#    steps:
#      - uses: actions/checkout@v1
#      - uses: ./.github/action-a
#        with:
#          MY_NAME: "Mona"