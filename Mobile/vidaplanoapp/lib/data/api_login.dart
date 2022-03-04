import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/data/graphql_configuration.dart';
import 'package:vidaplanoapp/models/user.dart';

class ApiLogin {
  Future<QueryResult> sendData(@required cpf, @required password) async {
    User loggedUser = User();
    String addData = '''
      mutation login (\$loginViaCPFInfo: LoginViaCPFInput, \$loggingInAs: AccessingEntity!) {
            login (loginViaCPFInfo: \$loginViaCPFInfo, loggingInAs: \$loggingInAs) {
                commonResponse {
                    isError
                    statusCode
                    description
                }
                authToken
                genericUser {
                    client {
                        id
                        email
                        name
                        cpf
                        rg
                        birthday
                        holder {
                            id
                            email
                            name
                            cpf
                            rg
                            birthday
                            holder {
                                id
                                email
                                name
                                cpf
                                rg
                                birthday
                                telephone
                                alive
                                clientType
                                createdAt
                                temporaryPassword
                                active
                                authorized
                                deleted
                                oneSignalPlayerId
                                gracePeriod
                                gender
                                luckNumber
                            }
                            telephone
                            alive
                            clientType
                            createdAt
                            temporaryPassword
                            active
                            authorized
                            deleted
                            oneSignalPlayerId
                            gracePeriod
                            documents {
                                id
                                urlDocument
                                documentClientType
                                active
                            }
                            dependents {
                                id
                                email
                                name
                                cpf
                                rg
                                birthday
                                telephone
                                alive
                                clientType
                                createdAt
                                temporaryPassword
                                active
                                deleted
                                oneSignalPlayerId
                                gender
                            }
                            gender
                            luckNumber
                        }
                        telephone
                        alive
                        clientType
                        createdAt
                        temporaryPassword
                        active
                        authorized
                        deleted
                        oneSignalPlayerId
                        gracePeriod
                        documents {
                            id
                            urlDocument
                            documentClientType
                            active
                        }
                        dependents {
                            id
                            email
                            name
                            cpf
                            rg
                            birthday
                            telephone
                            alive
                            clientType
                            createdAt
                            temporaryPassword
                            active
                            deleted
                            oneSignalPlayerId
                            documents {
                                id
                                urlDocument
                                documentClientType
                                active
                            }
                            gender
                        }
                        gender
                        luckNumber
                    }
                    farolUser {
                        id
                        email
                        name
                        cpf
                        telephone
                        agentNumber
                        active
                        isTemporaryPassword
                        permissionTypeDTOList
                    }
                }
            }
        }
      ''';

    String formattedCpf =
        cpf.toString().replaceAll(".", "").replaceAll("-", "");
    final variable = {
      "loginViaCPFInfo": {"cpf": formattedCpf, "password": password},
      "loggingInAs": "CLIENT"
    };

    GraphQlConfiguration configuration = GraphQlConfiguration();
    GraphQLClient client = configuration.clientToQuery();

    QueryResult queryResult = await client
        .query(QueryOptions(document: gql(addData), variables: variable));

    var dataset = queryResult.data?['login']['genericUser']['client'];
    //var jsonDataset = jsonDecode(dataset);
    loggedUser = User.fromJson(dataset);
    print(loggedUser.name);

    return queryResult;
  }
}
