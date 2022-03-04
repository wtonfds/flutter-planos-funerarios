# Instruções de como rodar o app

## deve-se instalar um docker e nele rodar o backend
## usando o postman deve usar os comandos de graphQL pra criar um usuario no banco de dados
### http://IPDASUAMAQUINA:8080/graphql/
## body da requisição
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
      
 ## GRAPHQL VARIABLES
    {
        "loginViaEmailData": {
          "email": "emaildecontato@gmail.com",
          "password": "123"
        },
        "cpf": "000.000.000-00",
        "name": "Wellington Ferreira",
        "telephone": "(84) 998184983",
        "birthDay": "19990608",
        "rg": "0000000",
        "gender": "MALE"
     }
 
