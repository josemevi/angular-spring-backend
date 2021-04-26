/*regiones*/
INSERT INTO regiones (id,nombre) VALUES (1,"Euskadi"),(2,"Canarias"),(3,"Aragón"),(4,"Illes Balears"),(5,"Galicia"),(6,"Comunitat Valenciana"),(7,"Catalunya"),(8,"Extremadura"),(9,"Madrid");
/*clientes*/
INSERT INTO clientes (nombre,apellido,email,create_at,region_id) VALUES ("Lewis","Knapp","ac.metus@purus.org","2020-11-23",4),("Castor","Hudson","ipsum@Morbinonsapien.org","1986-09-05",3),("Stone","Clayton","In@Nam.edu","2016-01-28",7),("Nina","Paul","Cum.sociis.natoque@velitAliquam.com","1998-07-17",7),("Shafira","Acevedo","ut@risus.com","1966-07-10",2),("Guinevere","Cote","pede@velit.com","1956-03-12",4),("Alan","Kim","Nunc@dolornonummy.com","2003-06-06",5),("Kevin","Craft","ut.quam.vel@Aeneangravida.ca","1983-09-26",1),("Dennis","James","Nullam.suscipit.est@semperegestas.org","2020-08-20",4),("Lara","Mccullough","Sed.diam.lorem@nectempus.edu","2015-09-17",1);
INSERT INTO clientes (nombre,apellido,email,create_at,region_id) VALUES ("Rudyard","Barron","arcu@Sed.org","1954-08-18",7),("Addison","Cannon","nibh.Phasellus.nulla@atarcu.ca","1965-10-11",9),("Ignacia","Dalton","in.hendrerit@Maurisblanditenim.co.uk","1975-12-31",2),("Daniel","Beard","lectus@Sednulla.co.uk","1949-11-16",1),("Ima","Glass","fermentum.arcu.Vestibulum@loremauctorquis.org","1977-06-12",2),("Shaeleigh","Cunningham","massa@dapibus.com","1988-11-08",1),("Grant","Middleton","est@orcilacus.org","2002-11-11",9),("Sara","Solomon","malesuada@dui.com","1988-09-27",5),("Randall","Mendoza","vitae@esttemporbibendum.co.uk","1945-04-20",9),("Keith","Bartlett","ornare@tempor.net","1993-07-24",1);
/*usuarios*/
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ("astaroth","$2a$10$MuN4SJwl9x.p3TqcAHR27.jIvvpyMr.Tq8sISFvANWbgv1Tw2TWC6",1,"Lalatina","Dustiness","Lalatina@kono.com"),("admin","$2a$10$15KxlsYYVIwx.IVYBvfYbetaf6zDBieNGHmj8.Qj2u6XzDmbrJjwG",1,"Aqua","Goddess","goddess@kono.com");
/*roles*/
INSERT INTO roles (nombre) VALUES ("ROLE_USER"),("ROLE_ADMIN");
/*assign roles*/
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1),(2, 2),(2, 1);
/*populate productos*/
INSERT INTO productos (nombre, precio, create_at) VALUES ("iPhone 13", 1999, NOW()),("Xiaomi Note 10",600,NOW()),("Xbox Series X",499,NOW()),("PlayStation 5",399,NOW()),("Lenovo Thinkpad l15",999,NOW()),("Samsung S20",999,NOW());
/*inserting facturas*/ 
INSERT INTO facturas (description, observation, cliente_id, create_at) VALUES("Factura telefonos",null,1,NOW());
INSERT into facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,1),(2,1,2);

INSERT INTO facturas (description, observation, cliente_id, create_at) VALUES("Factura consolas","Al cliente se le cayo la caja de un producto",1,NOW());
INSERT into facturas_items (cantidad, factura_id, producto_id) VALUES(1,2,3),(2,2,4);
