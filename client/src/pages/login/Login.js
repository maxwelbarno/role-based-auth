import "./Login.css";
import { useContext } from "react";
import AuthContext from "../../contexts/AuthContext";

const Login = () => {
  const { userLogin } = useContext(AuthContext);
  return (
    <div className="container">
      <header className="header">
        <h1>Sign In</h1>
      </header>
      <form className="form" onSubmit={userLogin}>
        <div className="form-row">
          <input
            type="text"
            name="username"
            id="username"
            placeholder="Enter Username"
          />
        </div>
        <div className="form-row">
          <input
            type="password"
            name="password"
            id="password"
            placeholder="Enter Password"
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
