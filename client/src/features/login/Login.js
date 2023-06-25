import "./Login.css";

const Login = () => {
  return (
    <div className="container">
      <header className="header">
        <h1>Sign In</h1>
      </header>
      <form action="" className="form">
        <div className="form-row">
          <input
            type="email"
            name="email"
            id="email"
            placeholder="Enter Email Address"
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
          <input type="button" name="button" value="Submit" id="submit" />
        </div>
      </form>
    </div>
  );
};

export default Login;
