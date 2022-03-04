import 'package:flutter/cupertino.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/data/graphql_configuration.dart';

class ApiPlans{

  
  Future<dynamic> sendData() async{
    String addData='''
        query listPlans (\$filterByID: Long, \$filterByName: String, \$paginate: Paginate) {
            listPlans (filterByID: \$filterByID, filterByName: \$filterByName, paginate: \$paginate) {
                commonResponse {
                    isError
                    statusCode
                    description
                }
                paginationInfo {
                    numberOfRecordsByPage
                    pageNumber
                    totalRecords
                }
                plansList {
                    id
                    name
                    gracePeriod
                    gracePeriodExtraDependents
                    maxExtraDependentsAmount
                    active
                    adhesionContract
                    upgradePriceTableDTO {
                        id
                        name
                        ageRanges {
                            id
                            startAge
                            endAge
                            value
                        }
                    }
                    planPriceTableDTO {
                        id
                        name
                        ageRanges {
                            id
                            startAge
                            endAge
                            value
                        }
                    }
                    dependentPriceTableDTO {
                        id
                        name
                        ageRanges {
                            id
                            startAge
                            endAge
                            value
                        }
                    }
                }
            }
        }
      ''';
    final variable = {
      "filterByID": "",
      "filterByName": "",
      "paginate": {
        "numberOfRecordsByPage": 0,
        "pageNumber": 0
      }
    };

    GraphQlConfiguration configuration = GraphQlConfiguration();
    GraphQLClient client = configuration.clientToQuery();

    QueryResult queryResult = await client.query(QueryOptions(document: gql(addData), variables: variable));
    print(queryResult);
    return queryResult.data;
  }
}