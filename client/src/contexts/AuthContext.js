import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();

  const [jwt, setJwt] = useState(() => {
    localStorage.getItem("jwt")
      ? JSON.parse(localStorage.getItem("jwt"))
      : null;
  });

  const [user, setUser] = useState(() => {
    localStorage.getItem("jwt")
      ? JSON.parse(localStorage.getItem("jwt"))
      : null;
  });

  const userLogin = async (event) => {
    event.preventDefault();
    const res = await fetch("http://localhost:8080/api/v1/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: event.target.username.value,
        password: event.target.password.value,
      }),
    });

    const { data } = await res.json();
    if (res.status === 200) {
      setJwt(data.accessToken);

      // Set user using decoded JWT
      setUser(jwt_decode(data.accessToken));

      localStorage.setItem("accessToken", JSON.stringify(data));
      navigate("/");
    }
  };

  const userLogout = () => {
    setJwt(null);
    setUser(null);
    localStorage.removeItem("accessToken");
    navigate("/login");
  };

  const contextData = { userLogin, user, userLogout };

  return (
    <AuthContext.Provider value={contextData}>{children}</AuthContext.Provider>
  );
};

export default AuthContext;
