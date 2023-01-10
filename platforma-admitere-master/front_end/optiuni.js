const sendS = document.querySelector("#sendList");

const url = window.location.href;
const searchParams = new URLSearchParams(url.substring(url.indexOf('?')));
const email = searchParams.get('email');
const encodedEmail = encodeURIComponent(email);
document.getElementById("myEmail").innerHTML = email.substring(0, email.indexOf("@"));

function getCookie(name) {
  const cookieString = document.cookie;
  const cookieRegex = new RegExp(`${name}=([^;]+)`);
  const cookieMatch = cookieString.match(cookieRegex);
  return cookieMatch ? cookieMatch[1] : null;
}
const jwt = getCookie("jwt");

var row;
function start()
{
  row = event.target;
}
function dragover(){
  var e = event;
  e.preventDefault();

  let children= Array.from(e.target.parentNode.parentNode.children);
  if(children.indexOf(e.target.parentNode)>children.indexOf(row))
    e.target.parentNode.after(row);
  else
    e.target.parentNode.before(row);
}
//

var res;
window.onload = async () => {
    await getList();
}

function printTable() {
    const tableBody = document.querySelector('tbody');

    const resArray = JSON.parse(res);

    for (let i = 0; i < resArray.length; i++) {
      const item = resArray[i];
      const row = document.createElement('tr');
      row.setAttribute('draggable', 'true');
      row.setAttribute('ondragstart', 'start()');
      row.setAttribute('ondragover', 'dragover()');

      const cell1 = document.createElement('td');
      cell1.textContent = item[0];
      row.appendChild(cell1);

      tableBody.appendChild(row);
    }
}

function getList()
{
    const jwt = getCookie("jwt");
    const bearer = `Bearer ${jwt}`;
    var myHeaders = new Headers();
    myHeaders.append("Authorization", bearer);
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };
    fetch("http://localhost:8081/afisare-specializari", requestOptions)
          .then((response) => {
            const status = response.status;
            if (status != 200) {
              alert("EROARE la primire! Code: " + status);
              return null;
            }
            return response.text();
          })
          .then((result) => {
            //console.log(result);
            res = result;
            printTable()
          })
}


if (sendS) {
  sendS.addEventListener("click", (e) => {
    e.preventDefault();
    const bearer = `Bearer ${jwt}`;

    const url = window.location.href;
    const searchParams = new URLSearchParams(url.substring(url.indexOf('?')));
    const email = searchParams.get('email');

    var myHeaders = new Headers();
    myHeaders.append("Authorization", bearer);

    const formData = new FormData();
    formData.append("email", JSON.stringify(email));
    const cells = document.querySelectorAll('tbody td:first-child');
    cells.forEach(cell => {
      formData.append('values[]', cell.textContent);
    });

    var requestOptions = {
      method: "POST",
      headers: myHeaders,
      body: formData,
      redirect: 'follow'
    };

  fetch("http://localhost:8081/trimitere-specializari", requestOptions)
      .then((response) => {
        const status = response.status;
        if (status != 200) {
          alert("EROARE la trimiterea specializarilor! Code: " + status);
          window.location = "http://localhost:8000/optiuni.html?email=" + encodedEmail;
          return null;
        }
        alert("Specializari trimise!");

        return response.text();
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
  });
}