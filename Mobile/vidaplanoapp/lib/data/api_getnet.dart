import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:vidaplanoapp/models/address.dart';
import 'package:vidaplanoapp/models/user.dart';

class ApiGetNet {
  final String urlHomolog = "https://api-homologacao.getnet.com.br";
  final String token = "";

  final User user;
  final Address address;
  final String planPrice;

  ApiGetNet(this.user, this.address, this.planPrice);

  getToken() async {
    BaseOptions options = new BaseOptions(baseUrl: urlHomolog, headers: {
      'Authorization': 'Bearer $token',
      'Content-Type': 'application/json'
    });

    Dio dio = new Dio(options);

    var response = await dio.post("/auth/oauth/v2/token");
  }

  payCreditCard() async {
    var body = json.encode({
      "seller_id": "f5576eb1-d665-4a00-b8fb-68d4bcd7cc50",
      "amount": planPrice,
      "currency": "BRL",
      "order": {
        "order_id": "6d2e4380-d8a3-4ccb-9138-c289182818a3",
        "sales_tax": 0,
        "product_type": "service"
      },
      "customer": {
        "customer_id": "customer_21081826",
        "first_name": user.name?.split(" ")[0],
        "last_name": user.name,
        "name": user.name,
        "email": user.email,
        "document_type": "CPF",
        "document_number": user.cpf,
        "phone_number": user.phone,
        "billing_address": {
          "street": "",
          "number": address.number,
          "complement": address.place,
          "district": address.district,
          "city": address.city,
          "state": address.state,
          "postal_code": "64000000"
        }
      },
      "device": {"ip_address": "127.0.0.1", "device_id": "hash-device-id"},
      "shippings": [
        {
          "customer_id": "customer_21081826",
          "first_name": user.name?.split(" ")[0],
          "last_name": user.name,
          "name": user.name,
          "email": user.email,
          "document_type": "CPF",
          "document_number": user.cpf,
          "phone_number": user.phone,
          "billing_address": {
            "street": "",
            "number": address.number,
            "complement": address.place,
            "district": address.district,
            "city": address.city,
            "state": address.state,
            "postal_code": "64000000"
          }
        },
      ],
      "sub_merchant": {
        "identification_code": "9058344",
        "document_type": "CNPJ",
        "document_number": "20551625000159",
        "address": "Torre Negra 44",
        "city": "Cidade",
        "state": "RS",
        "postal_code": "90520000"
      },
      "credit": {
        "delayed": false,
        "pre_authorization": false,
        "save_card_data": false,
        "transaction_type": "FULL",
        "number_installments": 1,
        "soft_descriptor": "LOJA*TESTE*COMPRA-123",
        "dynamic_mcc": 1799,
        "card": {
          "number_token":
              "dfe05208b105578c070f806c80abd3af09e246827d29b866cf4ce16c205849977c9496cbf0d0234f42339937f327747075f68763537b90b31389e01231d4d13c",
          "cardholder_name": "JOAO DA SILVA",
          "security_code": "123",
          "brand": "Mastercard",
          "expiration_month": "12",
          "expiration_year": "20"
        }
      }
    });

    BaseOptions options = new BaseOptions(baseUrl: urlHomolog, headers: {
      'Authorization': 'Bearer $token',
      'Content-Type': 'application/json'
    });

    Dio dio = new Dio(options);

    var response = await dio.post("/v1/payments/credit", data: body);
  }

  payBoleto() async {
    var body = json.encode({
      "seller_id": "f5576eb1-d665-4a00-b8fb-68d4bcd7cc50",
      "amount": planPrice,
      "currency": "BRL",
      "order": {
        "order_id": "6d2e4380-d8a3-4ccb-9138-c289182818a3",
        "sales_tax": 0,
        "product_type": "service"
      },
      "boleto": {
        "our_number": 1946598,
        "document_number": "170500000019763",
        "expiration_date": "16/11/2017",
        "instructions": "Não receber após o vencimento",
        "provider": "santander"
      },
      "customer": {
        "first_name": user.name?.split(" ")[0],
        "name": user.name,
        "document_type": "CPF",
        "document_number": user.cpf,
        "billing_address": {
          "street": "",
          "number": address.number,
          "complement": address.place,
          "district": address.district,
          "city": address.city,
          "state": address.state,
          "postal_code": "64000000"
        }
      }
    });

    BaseOptions options = new BaseOptions(baseUrl: urlHomolog, headers: {
      'Authorization': 'Bearer $token',
      'Content-Type': 'application/json'
    });

    Dio dio = new Dio(options);

    var response = await dio.post("/v1/payments/boleto", data: body);
  }
}
