import 'package:flutter/cupertino.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/data/graphql_configuration.dart';

class ApiSignin{

  Future<QueryResult> sendData(@required email, @required password, @required cpf, @required name, @required telephone, @required birthDay, @required rg, @required gender) async{
    String addData='''
        mutation registerNewClient (\$loginViaEmailData: LoginViaEmailInput, \$cpf: String!, \$name: String!, \$telephone: String!, \$birthDay: Date!, \$rg: String, \$gender: GenderDTO!) {
          registerNewClient (loginViaEmailData: \$loginViaEmailData, cpf: \$cpf, name: \$name, telephone: \$telephone, birthDay: \$birthDay, rg: \$rg, gender: \$gender) {
              commonResponse {
                  isError
                  statusCode
                  description
              }
              genericClient {
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
        "loginViaEmailData": {
          "email": email,
          "password": password
        },
        "cpf": cpf,
        "name": name,
        "telephone": telephone,
        "birthDay": birthDay,
        "rg": rg,
        "gender": gender
      };

    GraphQlConfiguration configuration = GraphQlConfiguration();
    GraphQLClient client = configuration.clientToQuery();

    QueryResult queryResult = await client.query(QueryOptions(document: gql(addData), variables: variable));
    print(queryResult);
    return queryResult;
  }
}