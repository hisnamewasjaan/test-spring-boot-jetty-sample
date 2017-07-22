#!/usr/bin/env bash
curl --header "content-type: text/xml" -d @sec-getCountryRequest.xml http://localhost:8080/ws

