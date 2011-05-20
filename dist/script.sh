#!/bin/bash
#Hugh Pearse

echo ""
echo "----------Headers:----------"
java -jar EmailParser.jar -p -h < rfc822.txt
echo ""
echo "----------Body:----------"
java -jar EmailParser.jar -f rfc822.txt -b
