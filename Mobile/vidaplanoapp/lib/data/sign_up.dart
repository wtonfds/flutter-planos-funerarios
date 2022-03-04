import 'package:flutter/cupertino.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:vidaplanoapp/data/graphql_configuration.dart';

class SignUp{

  Future<QueryResult> sendData(@required email, @required password, @required cpf, @required name, @required telephone) async{
    String addData='''
      mutation registerNewFarolUser (\$loginViaEmailData: LoginViaEmailInput, \$cpf: String!, \$name: String!, \$telephone: String!, \$permissions: [farolUserPermission]!) {
        registerNewFarolUser (loginViaEmailData: \$loginViaEmailData, cpf: \$cpf, name: \$name, telephone: \$telephone, permissions: \$permissions) {
            commonResponse {
                isError
                statusCode
                description
            }
            genericFarolUser {
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
      ''';
    final variable = {
      "loginViaEmailData": {
        "email": email,
        "password": password
      },
      "cpf": cpf,
      "name": name,
      "telephone": telephone,
      "permissions": [
        "ACCREDITED",
        "GENERAL_PARAMETERIZATION",
        "PRICE_TABLE",
        "LIST_CLIENTS"
      ]
    };

    GraphQlConfiguration configuration = GraphQlConfiguration();
    GraphQLClient client = configuration.clientToQuery();

    QueryResult queryResult = await client.query(QueryOptions(document: gql(addData), variables: variable));
    print(queryResult);
    return queryResult;
  }
}