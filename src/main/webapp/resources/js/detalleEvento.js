// Cuando la página cargue
document.addEventListener("DOMContentLoaded", () => {

    // Seleccionamos todos los selects de tipo de asiento
    const selectsTipo = document.querySelectorAll(".select-tipo");

    selectsTipo.forEach(select => {

        // Cada vez que cambie el tipo de asiento
        select.addEventListener("change", (event) => {

            const idTipo = event.target.value;          // Tipo seleccionado
            const index = event.target.dataset.index;   // Número de fila (1-5)

            // Selects asociados a esta fila
            const selectFila = document.querySelector(`.select-fila[data-index="${index}"]`);
            const selectAsiento = document.querySelector(`.select-asiento[data-index="${index}"]`);

            // Limpiamos selects
            selectFila.innerHTML = `<option value="">Selecciona la fila</option>`;
            selectAsiento.innerHTML = `<option value="">Selecciona el asiento</option>`;

            // Si no se ha seleccionado nada, no hacemos nada más
            if (!idTipo || !butacasData[idTipo]) return;

            const info = butacasData[idTipo];

            // Rellenar filas
            for (let f = 1; f <= info.filas; f++) {
                const option = document.createElement("option");
                option.value = f;
                option.textContent = f;
                selectFila.appendChild(option);
            }

            // Rellenar asientos
            for (let a = 1; a <= info.asientos; a++) {
                const option = document.createElement("option");
                option.value = a;
                option.textContent = a;
                selectAsiento.appendChild(option);
            }
        });
    });
});
