import "./Login.css";
import { useState } from "react";
const Login = () => {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  const userLogin = async (credentials) => {
    try {
      const res = await fetch("http://localhost:8080/api/v1/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(credentials),
      });
      const data = await res.json();
      return data;
    } catch (error) {
      console.log(error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await userLogin({ email, password });
  };

  return (
    <div className="container">
      <header className="header">
        <h1>Sign In</h1>
      </header>
      <form action="" className="form" onSubmit={handleSubmit}>
        <div className="form-row">
          <input
            type="email"
            name="email"
            id="email"
            placeholder="Enter Email Address"
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div className="form-row">
          <input
            type="password"
            name="password"
            id="password"
            placeholder="Enter Password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="form-row">
          <input type="submit" name="button" value="Submit" id="submit" />
        </div>
      </form>
    </div>
  );
};

export default Login;
