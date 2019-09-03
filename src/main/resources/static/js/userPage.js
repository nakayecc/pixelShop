window.onload = function () {
    let avatarBtn = document.querySelector("#playerInfoImg");
    let playerInfoText = document.getElementById("playerInfoText");
    let statisticBtn = document.querySelector("#statisticImg");
    let statisticText = document.getElementById("statisticText");


    avatarBtn.addEventListener('click', function () {
        showHide(playerInfoText);
    });

    statisticBtn.addEventListener('click', function () {
        showHide(statisticText);
    });


    function showHide(divId) {
        if (divId.style.display === "none" ||
            divId.style.display === "") {
            divId.style.display = "block";
        } else {
            divId.style.display = "none";
        }

    }


};