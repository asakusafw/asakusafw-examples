#!/bin/sh -e
#
# Copyright 2011-2016 Asakusa Framework Team.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

PROJECT_NAME=example-jdbc

if [ "$ASAKUSA_HOME" = "" ]
then
    echo '$ASAKUSA_HOME'" is not defined" 1>&2
    exit 1
fi

BASENAME=$(basename `pwd`)
if [ ! "$BASENAME" = "$PROJECT_NAME" ]
then
    echo "This script need to run on project-root directory." 1>&2
    exit 1
fi

rm "$ASAKUSA_HOME"/batchapps/* -fr
cp -pr target/batchc/* "$ASAKUSA_HOME"/batchapps

cp -a src/test/example-dataset /tmp
psql -f src/test/sql/import-example-dataset.sql
rm /tmp/example-dataset -fr

