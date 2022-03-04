import 'package:flutter/cupertino.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/data/graphql_configuration.dart';

class ApiDependent{

  Future<QueryResult> sendData(@required name, @required birthday, @required clientType, @required cpf, @required rg, @required telephone, @required gender) async{
    String addData='''
        mutation registerClientDependents (\$dependents: [DependentInput]!) {
          registerClientDependents (dependents: \$dependents) {
              commonResponse {
                  isError
                  statusCode
                  description
              }
              dependents {
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
          }
      }
      ''';
    final variable = {
            "dependents": {
              "name": name,
              "birthday": birthday,
              "clientType": clientType,
              "cpf": cpf,
              "rg": rg,
              "telephone": telephone,
              "documentsInput": {
                "image": "",
                "documentClientTypeDTO": ""
              },
              "gender": gender
            }
          };

    GraphQlConfiguration configuration = GraphQlConfiguration();
    GraphQLClient client = configuration.clientToQuery();

    QueryResult queryResult = await client.query(QueryOptions(document: gql(addData), variables: variable));
    print(queryResult);
    return queryResult;
  }
}