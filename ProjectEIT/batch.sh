echo "Enter name "
read -r name

truststore="${name}truststore"
keystore="${name}keystore"
csr="${name}.csr"
cer="${name}.cer"
caCert="certificate/ca-certificate.pem"
caKey="certificate/ca-key.pem"



#generate keypair
keytool -keystore $keystore -genkeypair -alias $name

#generate csr
keytool -keystore $keystore -certreq -alias $name -keyalg rsa -file $csr -storepass password

#sign
openssl x509 -req -CA $caCert -CAkey $caKey -in $csr -out $cer -days 365 -CAcreateserial

#import CA certificate to keystore
keytool -import -keystore $keystore -file $caCert -alias CA -storepass password

#import signed certificate
keytool -import -keystore $keystore -file $cer -alias $name  -storepass password

#import CA certificate to truststore
keytool -import -file $caCert -alias CA -keystore $truststore -storepass password