// import { getTokenFromLocalStorage } from "../lib/common";

// export const getAuthenticatedUser = async () => {
//   const defaultReturnObject = { authenticated: false };
//   try {
//     const token = getTokenFromLocalStorage();
//     if (!token) {
//       return defaultReturnObject;
//     }
//     const res = await fetch("http://localhost:8080/api/v1/users", {
//       method: "GET",
//       mode: "no-cors",
//       headers: {
//         Authorization: `Bearer ${token}`,
//       },
//     });
//     const { status } = await res.json();
//     console.log(status);
//     return status === "OK" ? true : false;
//   } catch (error) {
//     console.log("getAuthenticatedUser, Something Went Wrong", error);
//     return defaultReturnObject;
//   }
// };
