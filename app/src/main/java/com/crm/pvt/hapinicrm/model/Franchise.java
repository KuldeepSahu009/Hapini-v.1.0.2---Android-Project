package com.crm.pvt.hapinicrm.model;

public class Franchise {
   private String name;
    private String email;
    private String phoneno;
    private String whatsappno;
    private String passcode;
    private String password;
    private String location;
    private String imgurl;


        public Franchise() {

        }



        public Franchise(String name, String email, String phoneno, String whatsappno, String passcode, String password, String location, String imgurl) {
            this.name = name;
            this.email = email;
            this.phoneno = phoneno;
            this.whatsappno = whatsappno;
            this.passcode = passcode;
            this.password = password;
            this.location = location;
            this.imgurl = imgurl;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public String getWhatsappno() {
            return whatsappno;
        }

        public String getPasscode() {
            return passcode;
        }

        public String getPassword() {
            return password;
        }

        public String getLocation() {
            return location;
        }

        public String getImgurl() {
            return imgurl;
        }
}

