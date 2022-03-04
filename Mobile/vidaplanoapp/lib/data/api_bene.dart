import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:vidaplanoapp/models/address.dart';
import 'package:vidaplanoapp/models/user.dart';

class ApiBene{

  final String url = "https://clube.macroplataforma.com.br";
  final User user;
  final Address address;

  ApiBene(this.user, this.address);

  signUser() async {
    var body = json.encode({
        "user_id": "34587412589",
        "user_password": user.password,
        "user_name": user.name,
        "user_email": user.email,
        "user_email_secondary": "",
        "user_birth": user.bithdate,
        "user_phone": user.phone,
        "user_gender": "M",
        "user_zipcode": "04534000",
        "user_address": "",
        "user_number": address.number,
        "user_district": address.district,
        "user_complement": address.place,
        "user_city": address.city,
        "user_state": address.state,
        "user_registration_id": "15425",
        "user_office": "Gerente",
        "user_unit": "Matriz",
        "user_cost_center": ""
      });

    BaseOptions options = new BaseOptions(
        baseUrl: url,
        headers: {'Content-Type': 'application/json'});

    Dio dio = new Dio(options);

    var response = await dio
        .post("/api/v2/user/register", data: body);
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