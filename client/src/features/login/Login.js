import "./Login.css";

const Login = () => {
  return (
    <div className="container">
      <header class="header">
        <h1>Sign In</h1>
      </header>
      <form action="" class="form">
        <div class="form-row">
          <input
            type="email"
            name="full-name"
            id="full-name"
            placeholder="Enter Email Address"
          />
        </div>
        <div class="form-row">
          <input
            type="password"
            name="full-name"
            id="full-name"
            placeholder="Enter Password"
          />
        </div>
        <div class="form-row">
          <input type="button" name="button" value="Submit" id="submit" />
        </div>
      </form>
    </div>
  );
};

export default Login;
