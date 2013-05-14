This is a smpt only api for sending email, I decided not to use the JavaMail API
because it wasn't allowing for port connections other than 25 with the authenticator
(the transport does but then will not authenticate).  Also I regard the JavaMail API
as a mess there is no concept of a connection even though a socket connection is in 
there somewhere (it is both Session and Transport).  Also the Message is not detached
at all from the problem domain, requiring a connection (aka Session or Transport) to
create a message, so I just don't like the JavaMail API.