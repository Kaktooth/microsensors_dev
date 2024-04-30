package com.projects.microsensors.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstraints {

    @UtilityClass
    public class Authentication {

        public static final String GET_USER_BY_USERNAME_QUERY = """
                SELECT username, password, enabled FROM users WHERE username = ?
                """;
        public static final String GET_AUTHORITY_BY_USERNAME_QUERY = """
                SELECT username, authority FROM authorities
                WHERE username = ?
                """;
    }

    @UtilityClass
    public class Pagination {

        public static final int PAGE_SIZE = 9;

    }

    @UtilityClass
    public class ViewName {
        public static final String MAIN = "./main";
        public static final String LOG_IN = "./log-in";
        public static final String SIGN_UP = "./sign-up";
        public static final String DASHBOARD = "./dashboard";
        public static final String TERMS_OF_SERVICE = "./terms";
        public static final String PRIVACY_POLICY = "./privacy";
    }

    @UtilityClass
    public class Path {

        public static final String MAIN_PAGE = "/main";
        public static final String LOG_IN_PAGE = "/log-in";
        public static final String DASHBOARD_PAGE = "/dashboard";
        public static final String FAVICON = "/favicon.ico";
    }

    @UtilityClass
    public class ExtendedPath {

        public static final String TERMS_OF_SERVICE = "/main/terms/**";
        public static final String PRIVACY_POLICY = "/main/privacy/**";
        public static final String MAIN_PAGE = "/main/**";
        public static final String LOG_IN_PAGE = "/log-in/**";
        public static final String SIGN_UP_PAGE = "/sign-up/**";
        public static final String DASHBOARD_PAGE = "/dashboard/**";
        public static final String ABOUT_PAGE = "/dashboard/about/**";
        public static final String TUTORIAL_PAGE = "/dashboard/tutorial/**";
        public static final String ERROR_ATTRIBUTE = "?error";
        public static final String API = "/api/**";
        public static final String CSS = "/css/**";
        public static final String JS = "/js/**";
        public static final String STATIC = "/static/**";
    }

    @UtilityClass
    public class Sensors {

        public static final Integer DATA_LIMIT  = 200;
        public static final Integer MESSAGES_LIMIT = 100;
    }

    @UtilityClass
    public class FormattedCode {
        public static final String DHT22_Arduino = """
                #include <DHT.h>
                #include <ESP8266WiFi.h>
                #include <ESP8266WiFiMulti.h>
                #include <ESP8266HTTPClient.h>
                #include <WiFiClientSecureBearSSL.h>
                #include <ArduinoJson.h>
                #include <certs.h>
                #include <base64.h>
                                
                #define DHTPIN 13
                #define DHTTYPE DHT22
                DHT dht(DHTPIN, DHTTYPE);
                                
                #define WIFI_SSID "ssid"
                #define WIFI_PASS "pass"
                                
                #define DATA_URL  "/api/v1/sensor-data"
                #define MESSAGES_URL "/api/v1/sensor-messages"
                #define SENSOR_ID "sensor id uuid type "
                #define USER_KEY "your key uuid type"
                                
                HTTPClient https;
                ESP8266WiFiMulti WiFiMulti;
                WiFiClient basic_client;
                JsonDocument doc;
                                
                void setup() {
                                \n
                  Serial.begin(115200);
                  dht.begin();
                  Serial.println(F("DHT 22"));
                                
                  WiFi.mode(WIFI_STA);
                  WiFiMulti.addAP(WIFI_SSID, WIFI_PASS);
                  Serial.print("Connecting to: '" WIFI_SSID "'");
                                
                  post_message_to_service("Connected!");
                 \s
                  // initialize digital pin LED_BUILTIN as an output.
                  pinMode(LED_BUILTIN, OUTPUT);
                }
                                
                void loop() {
                                
                  if ((WiFiMulti.run() == WL_CONNECTED)) {
                    //led on
                    digitalWrite(LED_BUILTIN, HIGH); \s
                    delay(2000); 
                    // Reading temperature or humidity takes about 250 milliseconds!
                    // Sensor readings may also be up to 2 seconds 'old' (dht is a very slow sensor)
                    float h = dht.readHumidity();
                    // Read temperature as Celsius (the default)
                    float t = dht.readTemperature();
                    // Read temperature as Fahrenheit (isFahrenheit = true)
                    float f = dht.readTemperature(true);
                                
                    // Check if any reads failed and exit early (to try again).
                    if (isnan(h) || isnan(t) || isnan(f)) {
                      Serial.println(F("Failed to read from DHT sensor!"));
                      return;
                    }
                                
                    post_message_to_service("Humidity: " + String(h, 1) + "%" + " Temperature: " + String(t, 1) + "°C " +  String(f, 1) + "°F");
                   \s
                    String data = base64::encode(String(h, 1) + ' ' + String(t, 1) + ' ' + String(f, 1));
                    Serial.println(data);
                    post_data_to_service(data);
                                
                    //led off
                    digitalWrite(LED_BUILTIN, LOW);  \s
                    delay(2000);
                  }
                }
                                
                void post_data_to_service(String data) {
                  doc["data"] = data;
                  doc["sensorId"] = SENSOR_ID;
                  doc["key"] = USER_KEY;
                  String json;
                  serializeJson(doc, json);
                  post_request_to_service(json, DATA_URL);
                }
                                
                void post_message_to_service(String message) {
                  doc["message"] = message;
                  doc["sensorId"] = SENSOR_ID;
                  doc["key"] = USER_KEY;
                  String json;
                  serializeJson(doc, json);
                  post_request_to_service(json, MESSAGES_URL);
                }
                                
                void post_request_to_service(String json, String uri) {
                 \s
                  Serial.print("[HTTPS] begin...\\n");
                  std::unique_ptr<BearSSL::WiFiClientSecure> client(new BearSSL::WiFiClientSecure);
                  client->setFingerprint(fingerprint_open_sensor_data_systems);
                  if (https.begin(*client, open_sensors_data_host, open_sensors_data_port, uri, true)) {  // HTTPS
                                
                    https.addHeader("Content-Type", "application/json");
                    Serial.print("[HTTPS] post...\\n");
                                
                    // start connection and send header
                    int httpCode = https.POST(json);
                                
                    // httpCode will be negative on error
                    if (httpCode > 0) {
                      Serial.printf("[HTTPS] POST... code: %d\\n", httpCode);
                     \s
                      if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY || httpCode == HTTP_CODE_FOUND) {
                        String payload = https.getString();
                        Serial.println(payload);
                      }
                    } else {
                      Serial.printf("[HTTPS] POST... failed, error: %s\\n", https.errorToString(httpCode).c_str());
                    }
                               
                  https.end();
                  } else {
                    Serial.printf("[HTTPS] Unable to connect\\n");
                  }
                }
                """;
        public static final String CERT = """
                                
                // this file is autogenerated - any modification will be overwritten
                // unused symbols will not be linked in the final binary
                // generated on 2024-01-20 14:19:26
                // by ['cert.py', '-s', 'open-sensor-data.systems', '-n', 'open_sensors_data']
                                
                #pragma once
                                
                ////////////////////////////////////////////////////////////
                // certificate chain for open-sensor-data.systems:443
                                
                const char* open_sensors_data_host = "open-sensor-data.systems";
                const uint16_t open_sensors_data_port = 443;
                                
                // CN: open-sensor-data.systems => name: open_sensor_data_systems
                // not valid before: 2024-01-17 00:00:00
                // not valid after:  2025-01-08 23:59:59
                const char fingerprint_open_sensor_data_systems [] PROGMEM = "48:a7:d5:b7:d6:b6:c7:33:b6:60:5e:b7:19:d4:51:ca:6f:62:a5:8b";
                const char pubkey_open_sensor_data_systems [] PROGMEM = R"PUBKEY(
                -----BEGIN PUBLIC KEY-----
                MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzzTFIHssxotlBNFGIjlv
                Ye1iFA2wr0REc0cM29jk1l/JUP4d6R6LcM7Z3NMjsdLeatiFTh5fSyVsROSrX5/N
                atZH/pIpGNoLIMpKf7K8DYWel/QvAMQ7Ylfu34GOQQHGQoJ/0eUd9J9HmVlK44UH
                2DsP6IEc8Ox2gQGWl+ig0+2ijxinx7YkcNJAHI45RKsh1iL6d4Ac2M/DjuCYuTnY
                egDJPpiZUupMvirmazwJ+w+g3hpFKNwxXG0jsJJSbitsGbltyPF10HhhQefAR0nQ
                FTM8LddEp3+4UcwWZYFDSQaoAKk4IuYc18bWAEHbNCEE/1z8iRRlvO1yaqtcXPk3
                JQIDAQAB
                -----END PUBLIC KEY-----
                )PUBKEY";
                                
                // http://cacerts.digicert.com/EncryptionEverywhereG3TLSECCP384SHA3842023CA1.crt
                // CN: Encryption Everywhere G3 TLS ECC P384 SHA384 2023 CA1 => name: Encryption_Everywhere_G3_TLS_ECC_P384_SHA384_2023_CA1
                // not valid before: 2023-02-02 00:00:00
                // not valid after:  2033-02-01 23:59:59
                const char cert_Encryption_Everywhere_G3_TLS_ECC_P384_SHA384_2023_CA1 [] PROGMEM = R"CERT(
                -----BEGIN CERTIFICATE-----
                MIIDhzCCAwygAwIBAgIQArIKQS2PNGg7zeJI38GISjAKBggqhkjOPQQDAzBhMQsw
                CQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3d3cu
                ZGlnaWNlcnQuY29tMSAwHgYDVQQDExdEaWdpQ2VydCBHbG9iYWwgUm9vdCBHMzAe
                Fw0yMzAyMDIwMDAwMDBaFw0zMzAyMDEyMzU5NTlaMGYxCzAJBgNVBAYTAlVTMRcw
                FQYDVQQKEw5EaWdpQ2VydCwgSW5jLjE+MDwGA1UEAxM1RW5jcnlwdGlvbiBFdmVy
                eXdoZXJlIEczIFRMUyBFQ0MgUDM4NCBTSEEzODQgMjAyMyBDQTEwdjAQBgcqhkjO
                PQIBBgUrgQQAIgNiAARII987inRe+ApHRp22f1ldxRKYl3JsXo7EUZnvldeTa97w
                7c6H1QFkg4/eEudc7abpnvjSZuEh+fVB1WHehFXleaN2YE11NyMxZtqJuS7LY0KI
                TUmwNuIo40IZPIp5ruyjggGCMIIBfjASBgNVHRMBAf8ECDAGAQH/AgEAMB0GA1Ud
                DgQWBBSFrSQUKEDuuvUkzxLE56yu0AzLfTAfBgNVHSMEGDAWgBSz20ik+aHF2K42
                QcwRY2liKbxLxjAOBgNVHQ8BAf8EBAMCAYYwHQYDVR0lBBYwFAYIKwYBBQUHAwEG
                CCsGAQUFBwMCMHYGCCsGAQUFBwEBBGowaDAkBggrBgEFBQcwAYYYaHR0cDovL29j
                c3AuZGlnaWNlcnQuY29tMEAGCCsGAQUFBzAChjRodHRwOi8vY2FjZXJ0cy5kaWdp
                Y2VydC5jb20vRGlnaUNlcnRHbG9iYWxSb290RzMuY3J0MEIGA1UdHwQ7MDkwN6A1
                oDOGMWh0dHA6Ly9jcmwzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydEdsb2JhbFJvb3RH
                My5jcmwwPQYDVR0gBDYwNDALBglghkgBhv1sAgEwBwYFZ4EMAQEwCAYGZ4EMAQIB
                MAgGBmeBDAECAjAIBgZngQwBAgMwCgYIKoZIzj0EAwMDaQAwZgIxAJoCiwkT1zxv
                dI5Sq3zBjcXTiEHE78we1OBVmK8EbFodSIjS4ePT18IQee5+/yLSMwIxAPwpLjqD
                +Vy6jMVsV899dES29JXtseqjXKVs17jop9oeOzCVUwb/ofXVRDnUxOFi3g==
                -----END CERTIFICATE-----
                )CERT";
                                
                // http://cacerts.digicert.com/DigiCertGlobalRootG3.crt
                // CN: DigiCert Global Root G3 => name: DigiCert_Global_Root_G3
                // not valid before: 2013-08-01 12:00:00
                // not valid after:  2038-01-15 12:00:00
                const char cert_DigiCert_Global_Root_G3 [] PROGMEM = R"CERT(
                -----BEGIN CERTIFICATE-----
                MIICPzCCAcWgAwIBAgIQBVVWvPJepDU1w6QP1atFcjAKBggqhkjOPQQDAzBhMQsw
                CQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3d3cu
                ZGlnaWNlcnQuY29tMSAwHgYDVQQDExdEaWdpQ2VydCBHbG9iYWwgUm9vdCBHMzAe
                Fw0xMzA4MDExMjAwMDBaFw0zODAxMTUxMjAwMDBaMGExCzAJBgNVBAYTAlVTMRUw
                EwYDVQQKEwxEaWdpQ2VydCBJbmMxGTAXBgNVBAsTEHd3dy5kaWdpY2VydC5jb20x
                IDAeBgNVBAMTF0RpZ2lDZXJ0IEdsb2JhbCBSb290IEczMHYwEAYHKoZIzj0CAQYF
                K4EEACIDYgAE3afZu4q4C/sLfyHS8L6+c/MzXRq8NOrexpu80JX28MzQC7phW1FG
                fp4tn+6OYwwX7Adw9c+ELkCDnOg/QW07rdOkFFk2eJ0DQ+4QE2xy3q6Ip6FrtUPO
                Z9wj/wMco+I+o0IwQDAPBgNVHRMBAf8EBTADAQH/MA4GA1UdDwEB/wQEAwIBhjAd
                BgNVHQ4EFgQUs9tIpPmhxdiuNkHMEWNpYim8S8YwCgYIKoZIzj0EAwMDaAAwZQIx
                AK288mw/EkrRLTnDCgmXc/SINoyIJ7vmiI1Qhadj+Z4y3maTD/HMsQmP3Wyr+mt/
                oAIwOWZbwmSNuJ5Q3KjVSaLtx9zRSX8XAbjIho9OjIgrqJqpisXRAL34VOKa5Vt8
                sycX
                -----END CERTIFICATE-----
                )CERT";
                                
                                
                // end of certificate chain for open-sensor-data.systems:443
                ////////////////////////////////////////////////////////////
                                
                """;
    }
}
