#!/usr/bin/env bash
curl -u jan:pjan --header "content-type: text/xml" -d @sec-getCountryRequest.xml http://localhost:8080/ws

