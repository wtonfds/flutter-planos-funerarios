class Address {
  String? place;
  String? number;
  String? district;
  String? city;
  String? state;

  Address({this.place, this.number, this.district, this.city, this.state});

  Address.fromJson(Map<String, dynamic> json) {
    place = json['place'];
    number = json['number'];
    district = json['district'];
    city = json['city'];
    state = json['state'];
  }
}
