import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

const Header = () => {
  const { user, userLogout } = useContext(AuthContext);
  return (
    <div>
      <Link to="/">Home</Link>
      <span> | </span>
      {user ? (
        <a onClick={userLogout}>Logout</a>
      ) : (
        <Link to="/login">Sign In</Link>
      )}
      {user && <p>Hello {user?.name}</p>}
    </div>
  );
};

export default Header;
