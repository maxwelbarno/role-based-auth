// import { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { getAuthenticatedUser } from "../auth/api";

// export const useAuthentication = () => {
//   const [authenticated, setAuthenticated] = useState(false);
//   const navigate = useNavigate();

// //   console.log(authenticated);

//   useEffect(() => {
//     const getAuth = async () => {
//       const { authenticated } = await getAuthenticatedUser();
//       if (!authenticated) {
//         navigate("/");
//         return;
//       }
//       setAuthenticated(authenticated);
//     };
//     getAuth();
//   }, []);
//   return authenticated;
// };
