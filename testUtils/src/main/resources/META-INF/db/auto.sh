#!/usr/bin/env bash

rm -f auto.sql
cat modifyDatabase.sql >> auto.sql
cat 3-ENTRIES.sql >> auto.sql