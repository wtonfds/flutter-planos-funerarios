class User {
  String? name;
  String? cpf;
  String? bithdate;
  String? phone;
  String? email;
  String? password;

  User(
      {this.name,
      this.cpf,
      this.bithdate,
      this.phone,
      this.email,
      this.password});

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
        name: json['name'],
        cpf: json['cpf'],
        bithdate: json['bithdate'],
        phone: json['phone'],
        email: json['email'],
        password: json['password']);
  }
}
