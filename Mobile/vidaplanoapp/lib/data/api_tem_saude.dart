import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:vidaplanoapp/models/address.dart';
import 'package:vidaplanoapp/models/user.dart';

class ApiTemSaude{

  final String url = "https://qa.api.tempravoce.com";
  final User user;
  final Address address;

  ApiTemSaude(this.user, this.address);

  signUser() async {
    var body = json.encode(
      {
        "cpfTitular": "string",
        "CodOnix": 7065,
        "Nome": "JOAO FILHO DOS SANTOS",
        "cpf": "24269058080",
        "data_nascimento": "1975-07-01",
        "Sexo": 1,
        "email": "joaosanto@dominio.com.br",
        "NumeroCartao": 0,
        "cnpj": "87376109000106",
        "Logradouro": "string",
        "NumeroEndereco": "string",
        "Complemento": "string",
        "Bairro": "string",
        "Cidade": "string",
        "Estado": "string",
        "CEP": "09751000",
        "Telefone": "11982051990",
        "numerodasorte": 0,
        "tokenzeus": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDQ5NiIsImlhdCI6MTYzMjg0NzQwMCwiZXhwIjoxWzTuqCUDQ3NuT1dAjZuBFUzAwfQNjMyO4.P9KUmfxi8xa4HKHo598jLq2k",
        "cn": 0
      }
    );

    BaseOptions options = new BaseOptions(
        baseUrl: url,
        headers: {'Content-Type': 'application/json'});

    Dio dio = new Dio(options);

    var response = await dio
        .post("/tem_adesao", data: body);
  }


  generateDiscount() async {
    var body = json.encode({
      "partner_id": 123,
      "discount_id": 123456,
      "user_id": 127076867
    });

    BaseOptions options = new BaseOptions(
        baseUrl: url,
        headers: {'Content-Type': 'application/json'});

    Dio dio = new Dio(options);

    var response = await dio
        .post("/api/v2/generate-discounts", data: body);
  }
}