#!/usr/bin/env bash
heroku container:push web -a mickchat
heroku container:release web -a mickchat