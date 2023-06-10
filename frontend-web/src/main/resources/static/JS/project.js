setTimeout(()=>{
        const elements = document.getElementsByClassName("loginError");
    if (elements.length>0){
        for(let i=0; i<elements.length; i++) {
            const element = elements[i];
            if(element.tagName === "INPUT"){
                element.classList.remove('border-red-500')
                element.classList.add('border-oliveira')
                //element.value= ""; -- mete o input vazio
            }
            else if (element.tagName === "SPAN"){
                element.style.display = "none";
            }
            else if(element.tagName ==="DIV"){
                element.style.display= "none";
            }
        }
    }
},3000);

function logout(event) {
    const confirmed = confirm("Deseja encerrar a sessão?")
    if(!confirmed){
        event.preventDefault();
        return false;
    }
    console.log(confirmed)
}

function dropDownMenu() {
    const dropdown = document.getElementById("myDropdown");
    dropdown.classList.toggle("hidden");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches(".cursor-pointer")) {
        const dropdowns = document.getElementsByClassName("dropdown-content");
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (!openDropdown.classList.contains("hidden")) {
                openDropdown.classList.add("hidden");
            }
        }
    }
};


function toggleTableRow(icon) {
    const row = icon.closest('.grid').nextElementSibling

    if(row.classList.contains("hidden")) {
        row.classList.remove("hidden")
        row.classList.add("flex")
    }else {
        row.classList.remove("flex")
        row.classList.add("hidden")
    }
}

function showSuccessPopup() {
    var popup = document.getElementById('success-popup');
    popup.classList.remove('hidden');
}