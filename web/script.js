let queue = [];

const addBtn = document.getElementById("addBtn");
const processBtn = document.getElementById("processBtn");
const btnLabel = document.getElementById("btnLabel");
const spinner = document.getElementById("spinner");
const queueBody = document.getElementById("queueBody");
const queueCount = document.getElementById("statQueue");
const emptyQueue = document.getElementById("emptyQueue");
const resultsPanel = document.getElementById("resultsPanel");
const resultsBody = document.getElementById("resultsBody");

addBtn.addEventListener("click", () => {
    const fileName = document.getElementById("fileName").value.trim();
    if (!fileName) {
        alert("Please enter a file name.");
        return;
    }

    queue.push({
        fileName,
        documentType: document.getElementById("documentType").value,
        format: document.getElementById("format").value,
        country: document.getElementById("country").value
    });

    document.getElementById("fileName").value = "";
    renderQueue();
});

processBtn.addEventListener("click", async () => {
    setLoading(true);
    resultsPanel.hidden = true;

    try {
        const response = await fetch("/api/process-batch", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(queue)
        });

        const results = await response.json();
        renderResults(results);
    } catch (err) {
        alert("Error contacting the server: " + err);
    } finally {
        setLoading(false);
    }
});

function renderQueue() {
    queueBody.innerHTML = "";
    queueCount.textContent = queue.length;
    emptyQueue.hidden = queue.length > 0;
    processBtn.disabled = queue.length === 0;

    queue.forEach((doc, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${escapeHtml(doc.fileName)}</td>
            <td>${escapeHtml(doc.documentType)}</td>
            <td>.${escapeHtml(doc.format)}</td>
            <td>${escapeHtml(doc.country)}</td>
            <td><button class="remove-btn" data-index="${index}">Remove</button></td>
        `;
        queueBody.appendChild(row);
    });

    document.querySelectorAll(".remove-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            queue.splice(Number(btn.dataset.index), 1);
            renderQueue();
        });
    });
}

function renderResults(results) {
    resultsBody.innerHTML = "";

    results.forEach(r => {
        const row = document.createElement("tr");
        const statusClass = r.success ? "status-success" : "status-fail";
        const statusText = r.success ? "Success" : "Failed";

        row.innerHTML = `
            <td>${escapeHtml(r.fileName)}</td>
            <td>${escapeHtml(r.documentType)}</td>
            <td><span class="status-badge ${statusClass}">${statusText}</span></td>
            <td>${escapeHtml(r.message)}</td>
        `;
        resultsBody.appendChild(row);
    });

    resultsPanel.hidden = false;
}

function setLoading(isLoading) {
    processBtn.disabled = isLoading || queue.length === 0;
    spinner.hidden = !isLoading;
    btnLabel.textContent = isLoading ? "Processing..." : "Process Batch";
}

function escapeHtml(text) {
    const div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
}
