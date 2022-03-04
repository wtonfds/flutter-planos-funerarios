import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/utils/constants.dart';

class GraphQlConfiguration {

 GraphQLClient clientToQuery({String? sessionToken}) {

   var httpLink = HttpLink(apiUrl,
    //  headers: {
    //   'X-Parse-Application-Id' : kParseApplicationId,
    //   'X-Parse-Client-Key' : kParseClientKey,
    //   'X-Parse-Master-Key': kParseMasterKey,
    //   //'X-Parse-REST-API-Key' : kParseRestApiKey,
    // },
   );


   return GraphQLClient(
     cache: GraphQLCache(),
     link: httpLink,
   );
 }


}