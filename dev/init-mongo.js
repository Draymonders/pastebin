db.createUser(
{
  user: "draymonder",
  pwd: "123456",
  roles: [
    {role: "readWrite", db: "pastebin"}
  ]
}
);
