document.querySelector('.login-form').addEventListener('submit', function(e) {
  e.preventDefault(); // 阻止表单提交的默认行为

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  const loginData = {
    username: username,
    password: password
  };

  const  loginButton = document.querySelector(".login-form button");
  loginButton.disabled = true;
  loginButton.textContent = "登陆中...";

  console.log("Sending request with data:", loginData);

  // 发送 POST 请求到后端
  fetch('http://localhost:8080/login', {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loginData),
  })
  .then(response => {
    console.log("Response Status:", response.status);
    if (response.ok) {
      return response.json();
    } else {
      alert("发生错误");
    }
  })
  .then(data => {
    console.log("Response Data:", data);
    window.location.href = 'dashboard.html';
  })
  .catch(error => {
    console.error('请求失败:', error);
    alert(error.message); // 显示错误消息
  })
  .finally(() => {
    loginButton.disabled = "false";
    loginButton.textContent = "登陆";
  }) 
});
